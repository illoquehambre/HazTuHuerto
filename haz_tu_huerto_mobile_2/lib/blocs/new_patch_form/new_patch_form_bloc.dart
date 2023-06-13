import 'dart:async';
import 'package:flutter_form_bloc/flutter_form_bloc.dart';
import 'package:haz_tu_huerto_mobile_2/models/patch/new_patch_dto.dart';
import 'package:haz_tu_huerto_mobile_2/services/patch_service.dart';
import 'package:image_picker/image_picker.dart';


class NewPatchFormBloc extends FormBloc<String, DateTime> {
  final patchName = TextFieldBloc();
  final cultivationName = TextFieldBloc(validators: [
    FieldBlocValidators.required,
  ]);
  final variety = TextFieldBloc();
  final plantDate = TextFieldBloc<DateTime>();
  final harvestDate= TextFieldBloc<DateTime>();
  late List<XFile> files = [];
  final id;
  final PatchService _patchService;

  NewPatchFormBloc(PatchService patchService, this.id)
      // ignore: unnecessary_null_comparison
      : assert(patchService != null),
        _patchService = patchService {
    addFieldBlocs(fieldBlocs: [patchName, cultivationName, variety, plantDate, harvestDate]);
  }

  @override
  FutureOr<void> onSubmitting() {
    emitLoading();
    try {
      _patchService
          .create(files, NewPatchDto(patchName: patchName.value, cultivationName: cultivationName.value,
           variety: variety.value, plantDate: DateFormat('yyyy-MM-dd').parse(plantDate.value),
            harvestDate: DateFormat('yyyy-MM-dd').parse(harvestDate.value)),id)
          .then((value) {
        
      });
    } catch (e) {
      throw new Exception("Ha ocurrido un error en el bloc");
    }
  }
}
