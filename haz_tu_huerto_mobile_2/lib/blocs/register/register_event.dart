import 'package:equatable/equatable.dart';

abstract class RegisterEvent extends Equatable {
  @override
  List<Object> get props => [];
}

class SignUp extends RegisterEvent {
  final String username;
  final String password;
  final String verifyPassword;
  final String fullName;
  final String email;
  final String verifyEmail;

  SignUp({required this.username, required this.password, required this.verifyPassword,
         required this.fullName, required this.email, required this.verifyEmail });

  @override
  List<Object> get props => [username, password, verifyPassword, fullName, email, verifyEmail];
}
