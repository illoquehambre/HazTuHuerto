

import 'package:haz_tu_huerto_mobile_2/models/cultivation/cultivation_simplify_dto.dart';

class PatchSimplifyDto {
  late int id;
  late CultivationSimplifyDto cultivation;

  PatchSimplifyDto(
      { required this.id,
      required this.cultivation,
      });

  PatchSimplifyDto.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    cultivation = (json['cultivation'] != null
        ? CultivationSimplifyDto.fromJson(json['cultivation'])
        : null)!;
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['id'] = id;
    data['cultivation'] = cultivation.toJson();
    return data;
  }
  
}
