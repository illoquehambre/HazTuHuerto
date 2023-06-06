import 'package:get_it/get_it.dart';
import 'package:haz_tu_huerto_mobile_2/config/locator.dart';
import 'package:haz_tu_huerto_mobile_2/repositories/image_repository.dart';
import 'package:haz_tu_huerto_mobile_2/services/services.dart';
import 'package:image_picker/image_picker.dart';
import 'package:injectable/injectable.dart';



@Order(2)
@singleton
class ImageService {
  late ImageRepository _imageRepository;
  late LocalStorageService _localStorageService;

  ImageService() {
    _imageRepository = getIt<ImageRepository>();
    GetIt.I
        .getAsync<LocalStorageService>()
        .then((value) => _localStorageService = value);
  }


  
  Future<dynamic> download(String id) async {
    String? token = await _localStorageService.getFromDisk("user_token");

    if (token != null) {
      XFile response = await _imageRepository.download(id, token);
      return response;
    }
    throw Exception("There is an error in the service.");
  }

 
}