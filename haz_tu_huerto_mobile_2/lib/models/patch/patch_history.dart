
import 'package:haz_tu_huerto_mobile_2/models/cultivation/cultivation_dto.dart';

class PatchHistoryDto {
  late int id;
  late String name;
  late CultivationDto cultivation;
  //late int patchList;
  //String? refreshToken;

  PatchHistoryDto(
      { required this.id,
      required this.name,
      required this.cultivation
      // this.patchList,
      //this.refreshToken
      });

  PatchHistoryDto.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    name = json['name'];
    cultivation = (json['cultivation'] != null
        ? CultivationDto.fromJson(json['cultivation'])
        : null)!;
   
    //patchList = json['patchList'];

    //refreshToken = json['refreshToken'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['id'] = id;
    data['name'] = name;
    data['cultivation']=cultivation.toJson();
    //data['patchList'] = patchList;
   // data['refreshToken'] = refreshToken;
    return data;
  }
  
}
