

class CultivationSimplifyDto {
  late String cultivationName;
  late int daysLeft;

  CultivationSimplifyDto(
      { required this.cultivationName,
      required this.daysLeft,
      // this.patchList,
      //this.refreshToken
      });

  CultivationSimplifyDto.fromJson(Map<String, dynamic> json) {
    cultivationName = json['cultivationName'];
    daysLeft = json['daysLeft'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['cultivationName'] = cultivationName;
    data['daysLeft'] = daysLeft;
    //data['patchList'] = patchList;
   // data['refreshToken'] = refreshToken;
    return data;
  }
  
}
