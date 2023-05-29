
class NewQuestionDto {

  late String title;
  late String content;
  //String? refreshToken;

  NewQuestionDto(
      { 
      required this.title,
      required this.content,
      //this.refreshToken
      });

  NewQuestionDto.fromJson(Map<String, dynamic> json) {

    title = json['title'];
    content = json['content'];

    //refreshToken = json['refreshToken'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['title'] = title;
    data['content'] = content;
   // data['refreshToken'] = refreshToken;
    return data;
  }
  
}
