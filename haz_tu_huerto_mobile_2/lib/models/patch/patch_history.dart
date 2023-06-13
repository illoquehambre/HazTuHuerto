
import 'package:haz_tu_huerto_mobile_2/models/cultivation/cultivation_dto.dart';

class PatchHistoryDto {
  late int id;
  late String name;
  late List<CultivationDto> cultivationHistory;
  //late int patchList;
  //String? refreshToken;

  PatchHistoryDto(
      { required this.id,
      required this.name,
      required this.cultivationHistory
      // this.patchList,
      //this.refreshToken
      });

  PatchHistoryDto.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    name = json['name'];
    if (json['cultivationHistory'] != null) {
      cultivationHistory = <CultivationDto>[];
      json['cultivationHistory'].forEach((v) {
        cultivationHistory.add(CultivationDto.fromJson(v));
      });
    }
   
    //patchList = json['patchList'];

    //refreshToken = json['refreshToken'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['id'] = id;
    data['name'] = name;
    data['cultivation']=cultivationHistory.map((v) => v.toJson()).toList();
    //data['patchList'] = patchList;
   // data['refreshToken'] = refreshToken;
    return data;
  }
  
}
