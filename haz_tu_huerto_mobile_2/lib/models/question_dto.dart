import 'package:haz_tu_huerto_mobile_2/models/models.dart';

class QuestionDto {
  late String id;
  late String title;
  late String content;
  late String createdAt;
  late UserResponse publisher;
  late  int score;
  late int answers;
  //String? refreshToken;

  QuestionDto(
      { required this.id,
      required this.title,
      required this.content,
      required this.createdAt,
      required this.publisher,
      required this.score,
      required this.answers,
      //this.refreshToken
      });

  QuestionDto.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    title = json['title'];
    content = json['content'];
    createdAt = json['createdAt'];
    publisher = (json['publisher'] != null
        ? new UserResponse.fromJson(json['publisher'])
        : null)!;
    score = json['score'];
    answers = json['answers'];

    //refreshToken = json['refreshToken'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['id'] = id;
    data['title'] = title;
    data['content'] = content;
    data['createdAt'] = createdAt;
    data['publisher'] = publisher.toJson();
    data['score'] = score;
    data['answers'] = answers;
   // data['refreshToken'] = refreshToken;
    return data;
  }
  
}
