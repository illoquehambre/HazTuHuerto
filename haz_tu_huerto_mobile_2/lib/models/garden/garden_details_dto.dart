
import 'package:haz_tu_huerto_mobile_2/models/patch/patch_dto.dart';

class GardenDetailsDto {
  late String id;
  late String name;
  late String latitude;
  late String longitude;
  late String img;
  late  int numPatch;
  late List<PatchDto> patchList;
  //String? refreshToken;

  GardenDetailsDto(
      { required this.id,
      required this.name,
      required this.latitude,
      required this.longitude,
      required this.img,
      required this.numPatch,
      required this.patchList,
      //this.refreshToken
      });

  GardenDetailsDto.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    name = json['name'];
    latitude = json['latitude'] ?? '37.38614';
    longitude = json['longitude'] ?? '-5.99238';
    img = (json['img']);
    numPatch = json['numPatch'];
    patchList = (json['patchList'] != null
        ? json['patchList'].forEach((v) {
        patchList.add(PatchDto.fromJson(v));
      })
        : null)!;

    //refreshToken = json['refreshToken'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['id'] = id;
    data['name'] = name;
    data['latitude'] = latitude;
    data['longitude'] = longitude;
    data['img'] = img;
    data['numPatch'] = numPatch;
    data['patchList'] = patchList.map((v) => v.toJson()).toList();
   // data['refreshToken'] = refreshToken;
    return data;
  }
  
}
