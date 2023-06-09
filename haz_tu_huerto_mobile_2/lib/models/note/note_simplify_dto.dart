

import 'package:haz_tu_huerto_mobile_2/models/cultivation/cultivation_simplify_dto.dart';

class NoteSimplifyDto {
  late int id;
  late String title;

  NoteSimplifyDto(
      { required this.id,
      required this.title,
      });

  NoteSimplifyDto.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    title=json['title'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['id'] = id;
    data['title'] = title;
    return data;
  }
  
}
