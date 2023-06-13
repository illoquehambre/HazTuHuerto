import 'dart:async';
import 'package:flutter_form_bloc/flutter_form_bloc.dart';
import 'package:haz_tu_huerto_mobile_2/models/garden/new_garden_dto.dart';
import 'package:haz_tu_huerto_mobile_2/services/garden_service.dart';
import 'package:image_picker/image_picker.dart';


class NewGardenFormBloc extends FormBloc<String, String> {
  final name = TextFieldBloc(validators: [
    FieldBlocValidators.required,
  ]);
  late List<XFile> files = [];
  final GardenService _gardenService;

  NewGardenFormBloc(GardenService gardenService)
      // ignore: unnecessary_null_comparison
      : assert(gardenService != null),
        _gardenService = gardenService {
    addFieldBlocs(fieldBlocs: [name]);
  }

  @override
  FutureOr<void> onSubmitting() {
    emitLoading();
    try {
      _gardenService
          .create(files, NewGardenDto(name: name.value))
          .then((value) {
        
      });
    } catch (e) {
      throw new Exception("Ha ocurrido un error en el bloc");
    }
  }
}
