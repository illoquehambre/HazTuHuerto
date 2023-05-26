
class PatchDto {
  late int id;
  late String name;
  late String latitude;
  late String longitude;
  late String img;
  late  int numPatch;
  //late int patchList;
  //String? refreshToken;

  PatchDto(
      { required this.id,
      required this.name,
      required this.latitude,
      required this.longitude,
      required this.img,
      required this.numPatch,
      // this.patchList,
      //this.refreshToken
      });

  PatchDto.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    name = json['name'];
    latitude = json['latitude'] ?? '37.38614';
    longitude = json['longitude'] ?? '-5.99238';
    img = (json['img']);
    numPatch = json['numPatch'];
    //patchList = json['patchList'];

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
    //data['patchList'] = patchList;
   // data['refreshToken'] = refreshToken;
    return data;
  }
  
}
