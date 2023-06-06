
class NewGardenDto {

  late String name;
  //String? refreshToken;

  NewGardenDto(
      { 
      required this.name,
      //this.refreshToken
      });

  NewGardenDto.fromJson(Map<String, dynamic> json) {

    name = json['name'];

    //refreshToken = json['refreshToken'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['name'] = name;
   // data['refreshToken'] = refreshToken;
    return data;
  }
  
}
