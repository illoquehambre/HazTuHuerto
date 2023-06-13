
class NewNoteDto {

  late String title;
  late String content;
  //String? refreshToken;

  NewNoteDto(
      { 
      required this.title,
      required this.content,
      //this.refreshToken
      });

  NewNoteDto.fromJson(Map<String, dynamic> json) {

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
