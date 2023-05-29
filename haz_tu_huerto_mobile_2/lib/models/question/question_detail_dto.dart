import 'package:haz_tu_huerto_mobile_2/models/answer/answer_dto.dart';
import 'package:haz_tu_huerto_mobile_2/models/models.dart';
import 'package:http/http.dart';

class QuestionDetailsDto {
  late String id;
  late String title;
  late String content;
  late String urlImg;
  late String createdAt;
  late UserResponse publisher;
  late  int score;
  late List<AnswerDto> answers;
  late bool likedByLoguedUser;
  //String? refreshToken;

  QuestionDetailsDto(
      { required this.id,
      required this.title,
      required this.content,
      required this.urlImg,
      required this.createdAt,
      required this.publisher,
      required this.score,
      required this.answers,
      required this.likedByLoguedUser
      //this.refreshToken
      });

  QuestionDetailsDto.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    title = json['title'];
    content = json['content'];
    urlImg = json['urlImg'];
    createdAt = json['createdAt'];
    publisher = (json['publisher'] != null
        ? UserResponse.fromJson(json['publisher'])
        : null)!;
    score = json['score'];
    if (json['answers'] != null) {
      answers = <AnswerDto>[];
      json['answers'].forEach((v) {
        answers.add(AnswerDto.fromJson(v));
      });
    likedByLoguedUser = json['likedByLoguedUser'];
    //refreshToken = json['refreshToken'];
    }
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['id'] = id;
    data['title'] = title;
    data['content'] = content;
    data['urlImg'] = urlImg;
    data['createdAt'] = createdAt;
    data['publisher'] = publisher.toJson();
    data['score'] = score;
    data['answers'] = answers.map((v) => v.toJson()).toList();
   // data['refreshToken'] = refreshToken;
    return data;
  }
  
}
