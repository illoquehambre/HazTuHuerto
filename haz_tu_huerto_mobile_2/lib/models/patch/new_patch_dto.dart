class NewPatchDto {
  late String patchName;
  late String cultivationName;
  late String variety;
  late DateTime plantDate;
  late DateTime harvestDate;
  //String? refreshToken;

  NewPatchDto({
    required this.patchName,
    required this.cultivationName,
    required this.variety,
    required this.plantDate,
    required this.harvestDate,
    //this.refreshToken
  });

  NewPatchDto.fromJson(Map<String, dynamic> json) {
    patchName = json['patchName'];
    cultivationName = json['cultivationName'];
    variety = json['variety'];
    plantDate = json['plantDate'];
    harvestDate = json['harvestDate'];

    //refreshToken = json['refreshToken'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['patchName'] = patchName;
    data['cultivationName'] = cultivationName;
    data['variety'] = variety;
    data['plantDate'] = plantDate;
    data['harvestDate'] = harvestDate;

    // data['refreshToken'] = refreshToken;
    return data;
  }
}
