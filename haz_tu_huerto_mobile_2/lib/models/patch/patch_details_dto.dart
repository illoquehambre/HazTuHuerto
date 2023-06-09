
import 'package:haz_tu_huerto_mobile_2/models/cultivation/cultivation_details_dto.dart';
import 'package:haz_tu_huerto_mobile_2/models/cultivation/cultivation_dto.dart';

class PatchDetailsDto {
  late int id;
  late String name;
  late String gardenName;
  late CultivationDetailsDto cultivation;
  //late int patchList;
  //String? refreshToken;

  PatchDetailsDto(
      { required this.id,
      required this.name,
      required this.cultivation
      // this.patchList,
      //this.refreshToken
      });

  PatchDetailsDto.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    name = json['name'];
    cultivation = (json['cultivation'] != null
        ? CultivationDetailsDto.fromJson(json['cultivation'])
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
