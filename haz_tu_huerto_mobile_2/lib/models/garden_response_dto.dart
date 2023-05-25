
import 'garden_dto.dart';


class GardenResponseDto {
  late List<GardenDto> content;
  //late int currentPage;
  late bool last;
  late bool first;
  late int totalPages;
  late int totalElements;

  GardenResponseDto(
      {required this.content,
     // required this.currentPage,
      required this.last,
      required this.first,
      required this.totalPages,
      required this.totalElements});

  GardenResponseDto.fromJson(Map<String, dynamic> json) {
    if (json['content'] != null) {
      content = <GardenDto>[];
      json['content'].forEach((v) {
        content.add(GardenDto.fromJson(v));
      });
    }
   // currentPage = json['currentPage'];
    last = json['last'];
    first = json['first'];
    totalPages = json['totalPages'];
    totalElements = json['totalElements'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['content'] = content.map((v) => v.toJson()).toList();
   // data['currentPage'] = this.currentPage;
    data['last'] = last;
    data['first'] = first;
    data['totalPages'] = totalPages;
    data['totalElements'] = totalElements;
    return data;
  }
}
/*
class Content {
  int? id;
  String? affair;
  String? content;
  List<String>? imgPath;
  UserWhoPost? userWhoPost;
  int? usersWhoLiked;
  int? comments;
  bool? likedByUser;
  String? postDate;

  Content(
      {this.id,
      this.affair,
      this.content,
      this.imgPath,
      this.userWhoPost,
      this.usersWhoLiked,
      this.comments,
      this.likedByUser,
      this.postDate});

  Content.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    affair = json['affair'];
    content = json['content'];
    imgPath = json['imgPath'].cast<String>();
    userWhoPost = json['userWhoPost'] != null
        ? new UserWhoPost.fromJson(json['userWhoPost'])
        : null;
    usersWhoLiked = json['usersWhoLiked'];
    comments = json['comments'];
    likedByUser = json['likedByUser'];
    postDate = json['postDate'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['id'] = this.id;
    data['affair'] = this.affair;
    data['content'] = this.content;
    data['imgPath'] = this.imgPath;
    if (this.userWhoPost != null) {
      data['userWhoPost'] = this.userWhoPost!.toJson();
    }
    data['usersWhoLiked'] = this.usersWhoLiked;
    data['comments'] = this.comments;
    data['likedByUser'] = this.likedByUser;
    data['postDate'] = this.postDate;
    return data;
  }
}
*/
