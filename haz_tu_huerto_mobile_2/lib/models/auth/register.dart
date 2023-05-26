class RegisterResponse {
  String? id;
  String? username;
  String? fullName;
  String? createdAt;
  String? avatar;
  String? token;
  //String? refreshToken;

  RegisterResponse(
      {this.id,
      this.username,
      this.fullName,
      this.createdAt,
      this.avatar,
      });

  RegisterResponse.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    username = json['username'];
    fullName = json['fullName'];
    createdAt = json['createdAt'];
    avatar = json['avatar'];
    //refreshToken = json['refreshToken'];
  }




  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['id'] = id;
    data['username'] = username;
    data['fullName'] = fullName;
    data['avatar'] = avatar;
   // data['refreshToken'] = refreshToken;
    return data;
  }
  
}


class RegisterRequest {
  String? username;
  String? password;
  String? verifyPassword;
  String? fullName;
  String? email;
  String? verifyEmail;


  RegisterRequest({this.username, this.password, this.verifyPassword, this.fullName, this.email, this.verifyEmail});

  RegisterRequest.fromJson(Map<String, dynamic> json) {
    username = json['username'];
    password = json['password'];
    verifyPassword = json['verifyPassword'];
    fullName = json['fullName'];
    email = json['email'];
    verifyEmail = json['verifyEmail'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['username'] = username;
    data['password'] = password;
    data['verifyPassword'] = verifyPassword;
    data['fullName'] = fullName;
    data['email'] = email;
    data['verifyEmail'] = verifyEmail;

    return data;
  }
}
