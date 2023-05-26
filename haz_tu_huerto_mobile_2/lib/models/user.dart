
/*class User {
  final String name;
  final String email;
  final String accessToken;
  final String? avatar;

  User({required this.name, required this.email, required this.accessToken, this.avatar});

  @override
  String toString() => 'User { name: $name, email: $email}';
}*/


import 'auth/login.dart';

class User {
  String? id;
  String? username;
  String? avatar;
  String? fullName;

  User({this.id, this.username, this.avatar, this.fullName});

    User.fromLoginResponse(LoginResponse response) {
      id = response.id;
      username = response.username;
      avatar = response.avatar;
      fullName = response.fullName;
    }
}

class UserResponse extends User {

  UserResponse(id, username, fullName, avatar) : super(id: id, username: username, fullName: fullName, avatar: avatar);

  UserResponse.fromJson(Map<String, dynamic> json) {
  id = json['id'];
  username = json['username'];
  avatar = json['avatar'];
  fullName = json['fullName'];
}
  Map<String, dynamic> toJson() {
  final Map<String, dynamic> data = <String, dynamic>{};
  data['id'] = id;
  data['username'] = username;
  data['avatar'] = avatar;
  data['fullName'] = fullName;
  return data;
}

}
