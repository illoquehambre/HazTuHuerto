import 'dart:convert';
import 'package:get_it/get_it.dart';
import 'package:haz_tu_huerto_mobile_2/models/models.dart';
import 'package:haz_tu_huerto_mobile_2/rest/rest_client.dart';
import 'package:injectable/injectable.dart';



@Order(-1)
@singleton
class AuthenticationRepository {
  late RestClient _client;

  AuthenticationRepository() {
    _client = GetIt.I.get<RestClient>();
    //_client = RestClient();
  }

  Future<dynamic> doLogin(String username, String password) async {
    String url = "/auth/login";

    var jsonResponse = await _client.post(
        url, LoginRequest(username: username, password: password));
    return LoginResponse.fromJson(jsonDecode(jsonResponse));
  }

  Future<dynamic> doRegister(
      String username,
      String password,
      String verifyPassword,
      String fullName,
      String email,
      String verifyEmail) async {
    String url = "/auth/register";

    var jsonResponse = await _client.singUpPost(
        url,
        RegisterRequest(
            username: username,
            email: email,
            verifyEmail: verifyEmail,
            fullName: fullName,
            password: password,
            verifyPassword: verifyPassword));
    return jsonResponse;
  }
}
