import 'dart:convert';
//import 'dart:developer';

import 'package:get_it/get_it.dart';
import 'package:injectable/injectable.dart';

//import '../exceptions/exceptions.dart';

import '../config/locator.dart';
import '../models/register.dart';
import '../models/login.dart';
import '../models/user.dart';
import '../repositories/authentication_repository.dart';
import '../repositories/user_repository.dart';
import 'localstorage_service.dart';
import 'package:http/http.dart' as http;

abstract class AuthenticationService {
  Future<User?> getCurrentUser();
  Future<User> signInWithEmailAndPassword(String email, String password);
  Future<http.Response> signUp(String username, String password,
      String verifyPassword, String fullName, String email, String verifyEmail);
  Future<void> signOut();
}
/*
class FakeAuthenticationService extends AuthenticationService {
  @override
  Future<User?> getCurrentUser() async {
    return null; // return null for now
  }

  @override
  Future<User> signInWithEmailAndPassword(String email, String password) async {
    await Future.delayed(Duration(seconds: 1)); // simulate a network delay

    if (email.toLowerCase() != 'test@domain.com' || password != 'testpass123') {
      throw AuthenticationException(message: 'Wrong username or password');
    }
    return User(name: 'Test User', email: email);
  }

  @override
  Future<void> signOut() async {
    log("logout");
  }
}
*/

@Order(2)
//@Singleton(as: AuthenticationService)
@singleton
class JwtAuthenticationService extends AuthenticationService {
  late AuthenticationRepository _authenticationRepository;
  late LocalStorageService _localStorageService;
  late UserRepository _userRepository;

  JwtAuthenticationService() {
    _authenticationRepository = getIt<AuthenticationRepository>();
    _userRepository = getIt<UserRepository>();
    GetIt.I
        .getAsync<LocalStorageService>()
        .then((value) => _localStorageService = value);
  }

  @override
  Future<User?> getCurrentUser() async {
    //String? loggedUser = _localStorageService.getFromDisk("user");
    print("get current user");
    String? token = _localStorageService.getFromDisk("user_token");
    if (token != null) {
      /*
      UserResponse response = await _userRepository.me();
      return response;
      */
      return null;
    }
    return null;
  }

  @override
  Future<User> signInWithEmailAndPassword(String email, String password) async {
    LoginResponse response =
        await _authenticationRepository.doLogin(email, password);
    //await _localStorageService.saveToDisk('user', jsonEncode(response.toJson()));
    await _localStorageService.saveToDisk('user_token', response.token);
    return User.fromLoginResponse(response);
  }

  @override
  Future<http.Response> signUp(
      String username,
      String password,
      String verifyPassword,
      String fullName,
      String email,
      String verifyEmail) async {
      var response = await _authenticationRepository.doRegister(
        username, password, verifyPassword, fullName, email, verifyEmail);
    //await _localStorageService.saveToDisk('user', jsonEncode(response.toJson()));
    // await _localStorageService.saveToDisk('user_token', response.token);
    return response;
  }

  @override
  Future<void> signOut() async {
    print("borrando token");
    await _localStorageService.deleteFromDisk("user_token");
  }
}
