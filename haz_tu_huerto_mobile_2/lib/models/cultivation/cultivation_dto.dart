


class CultivationDto {
  late String name;
  late String plantDate;
  late String harvestDate;
  late int daysLeft;
  late int daysPlanted;
  late String cultivationImg;//Esto verdaderamente debe ser una imagen no un string
  late  int numNotes;
  //late int patchList;
  //String? refreshToken;

  CultivationDto(
      { required this.name,
      required this.plantDate,
      required this.harvestDate,
      required this.daysLeft,
      required this.daysPlanted,
      required this.cultivationImg,
      required this.numNotes,
      // this.patchList,
      //this.refreshToken
      });

  CultivationDto.fromJson(Map<String, dynamic> json) {
    name = json['name'];
    plantDate = json['plantDate'];
    harvestDate = json['harvestDate'];
    daysLeft = json['daysLeft'];
    daysPlanted = (json['daysPlanted']);
    cultivationImg = json['cultivationImg'];
    numNotes = json['numNotes'];
    //patchList = json['patchList'];

    //refreshToken = json['refreshToken'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['name'] = name;
    data['plantDate'] = plantDate;
    data['harvestDate'] = harvestDate;
    data['daysLeft'] = daysLeft;
    data['daysPlanted'] = daysPlanted;
    data['cultivationImg'] = cultivationImg;
    data['numNotes'] = numNotes;
    //data['patchList'] = patchList;
   // data['refreshToken'] = refreshToken;
    return data;
  }
  
}
