class GetPostDto {
  late int id;
  late String affair;
  late String content;
  late List<String> imgPath;
  late UserWhoPost userWhoPost;
  late int usersWhoLiked;
  late int comments;
  late bool likedByUser;
  late String postDate;

  GetPostDto(
      {required this.id,
      required this.affair,
      required this.content,
      required this.imgPath,
      required this.userWhoPost,
      required this.usersWhoLiked,
      required this.comments,
      required this.likedByUser,
      required this.postDate});

  GetPostDto.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    affair = json['affair'];
    content = json['content'];
    imgPath = json['imgPath'].cast<String>();
    userWhoPost = (json['userWhoPost'] != null
        ? UserWhoPost.fromJson(json['userWhoPost'])
        : null)!;
    usersWhoLiked = json['usersWhoLiked'];
    comments = json['comments'];
    likedByUser = json['likedByUser'];
    postDate = json['postDate'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['id'] = id;
    data['affair'] = affair;
    data['content'] = content;
    data['imgPath'] = imgPath;
    data['userWhoPost'] = userWhoPost.toJson();
    data['usersWhoLiked'] = usersWhoLiked;
    data['comments'] = comments;
    data['likedByUser'] = likedByUser;
    data['postDate'] = postDate;
    return data;
  }
}

class UserWhoPost {
  late String userName;
  late String imgPath;
  late bool verified;

  UserWhoPost(
      {required this.userName, required this.imgPath, required this.verified});

  UserWhoPost.fromJson(Map<String, dynamic> json) {
    userName = json['userName'];
    imgPath = json['imgPath'];
    verified = json['verified'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['userName'] = userName;
    data['imgPath'] = imgPath;
    data['verified'] = verified;
    return data;
  }
}
