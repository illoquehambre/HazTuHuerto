


class NoteResponseDto {
  late int id;
  late String title;
  late String content;
  late String cultivationName;
  late String lastModifedDate;
  NoteResponseDto(
      { required this.id,
      required this.title,
      required this.content,
      required this.cultivationName,
      required this.lastModifedDate,
      });

  NoteResponseDto.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    title=json['title'];
    content=json['content'];
    cultivationName=json['cultivationName'];
    lastModifedDate=json['lastModifedDate'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['id'] = id;
    data['title'] = title;
    data['content'] = content;
    data['cultivationName'] = cultivationName;
    data['lastModifedDate'] = lastModifedDate;
    return data;
  }
  
}
