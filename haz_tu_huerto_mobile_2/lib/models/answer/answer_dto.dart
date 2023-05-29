import 'package:haz_tu_huerto_mobile_2/models/models.dart';
import 'package:haz_tu_huerto_mobile_2/models/question/question_dto.dart';

class AnswerDto {
  late String id;
  late String content;
  late String createdAt;
  late UserResponse publisher;
  late QuestionDto question;
  //String? refreshToken;

  AnswerDto(
      { required this.id,
      required this.content,
      required this.createdAt,
      required this.publisher,
      required this.question,
      //this.refreshToken
      });

  AnswerDto.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    content = json['content'];
    createdAt = json['createdAt'];
    publisher = (json['publisher'] != null
        ? UserResponse.fromJson(json['publisher'])
        : null)!;
    question = (json['question'] != null
        ? QuestionDto.fromJson(json['question'])
        : null)!;
    //refreshToken = json['refreshToken'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['id'] = id;
    data['content'] = content;
    data['createdAt'] = createdAt;
    data['publisher'] = publisher.toJson();
    data['answers'] = question.toJson();
   // data['refreshToken'] = refreshToken;
    return data;
  }
  
}
