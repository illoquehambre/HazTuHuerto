import 'dart:async';
import 'package:flutter_form_bloc/flutter_form_bloc.dart';
import 'package:haz_tu_huerto_mobile_2/models/garden/new_garden_dto.dart';
import 'package:haz_tu_huerto_mobile_2/services/garden_service.dart';
import 'package:image_picker/image_picker.dart';


class UpdateGardenFormBloc extends FormBloc<String, String> {
  final name = TextFieldBloc();
  late List<XFile> files = [];
  final GardenService _gardenService;
  final String id;

  UpdateGardenFormBloc(GardenService gardenService, this.id)
      // ignore: unnecessary_null_comparison
      : assert(gardenService != null && id != null),      
        _gardenService = gardenService {
    addFieldBlocs(fieldBlocs: [name]);
  }

  @override
  FutureOr<void> onSubmitting() {
    emitLoading();
    try {
      _gardenService
          .update(files, NewGardenDto(name: name.value), id)
          .then((value) {
        
      });
    } catch (e) {
      throw new Exception("Ha ocurrido un error en el bloc");
    }
  }
}
