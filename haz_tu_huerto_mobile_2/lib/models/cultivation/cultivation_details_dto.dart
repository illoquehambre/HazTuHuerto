


import 'package:haz_tu_huerto_mobile_2/models/note/note_simplify_dto.dart';

class CultivationDetailsDto {
  late String name;
  late String plantDate;
  late String harvestDate;
  late int daysLeft;
  late int daysPlanted;
  late String cultivationImg;//Esto verdaderamente debe ser una imagen no un string
  late  List<NoteSimplifyDto> notes;
  //late int patchList;
  //String? refreshToken;

  CultivationDetailsDto(
      { required this.name,
      required this.plantDate,
      required this.harvestDate,
      required this.daysLeft,
      required this.daysPlanted,
      required this.cultivationImg,
      required this.notes,
      // this.patchList,
      //this.refreshToken
      });

  CultivationDetailsDto.fromJson(Map<String, dynamic> json) {
    name = json['name'];
    plantDate = json['plantDate']?? '12-12-2012';
    harvestDate = json['harvestDate']?? '12-12-2012';
    daysLeft = json['daysLeft'];
    daysPlanted = (json['daysPlanted']);
    cultivationImg = json['cultivationImg'];
    if (json['notes'] != null){
       notes = <NoteSimplifyDto>[];
        json['notes'].forEach((v) {
        notes.add(NoteSimplifyDto.fromJson(v));
      });
        
    }
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
    data['notes'] = notes.map((v) => v.toJson()).toList();
    //data['patchList'] = patchList;
   // data['refreshToken'] = refreshToken;
    return data;
  }
  
}
