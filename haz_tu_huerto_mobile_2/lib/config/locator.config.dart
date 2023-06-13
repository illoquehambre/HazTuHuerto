// GENERATED CODE - DO NOT MODIFY BY HAND

// **************************************************************************
// InjectableConfigGenerator
// **************************************************************************

// ignore_for_file: unnecessary_lambdas
// ignore_for_file: lines_longer_than_80_chars
// coverage:ignore-file

// ignore_for_file: no_leading_underscores_for_library_prefixes
import 'package:get_it/get_it.dart' as _i1;
import 'package:haz_tu_huerto_mobile_2/repositories/authentication_repository.dart'
    as _i4;
import 'package:haz_tu_huerto_mobile_2/repositories/garden_repository.dart'
    as _i5;
import 'package:haz_tu_huerto_mobile_2/repositories/image_repository.dart'
    as _i6;
import 'package:haz_tu_huerto_mobile_2/repositories/note_repository.dart'
    as _i7;
import 'package:haz_tu_huerto_mobile_2/repositories/patch_repository.dart'
    as _i8;
import 'package:haz_tu_huerto_mobile_2/repositories/question_repository.dart'
    as _i9;
import 'package:haz_tu_huerto_mobile_2/repositories/user_repository.dart'
    as _i10;
import 'package:haz_tu_huerto_mobile_2/rest/rest_client.dart' as _i3;
import 'package:haz_tu_huerto_mobile_2/services/authentication_service.dart'
    as _i13;
import 'package:haz_tu_huerto_mobile_2/services/garden_service.dart' as _i11;
import 'package:haz_tu_huerto_mobile_2/services/image_service.dart' as _i12;
import 'package:haz_tu_huerto_mobile_2/services/note_service.dart' as _i14;
import 'package:haz_tu_huerto_mobile_2/services/patch_service.dart' as _i15;
import 'package:haz_tu_huerto_mobile_2/services/question_service.dart' as _i16;
import 'package:injectable/injectable.dart' as _i2;

extension GetItInjectableX on _i1.GetIt {
  // initializes the registration of main-scope dependencies inside of GetIt
  _i1.GetIt init({
    String? environment,
    _i2.EnvironmentFilter? environmentFilter,
  }) {
    final gh = _i2.GetItHelper(
      this,
      environment,
      environmentFilter,
    );
    gh.singleton<_i3.RestAuthenticatedClient>(_i3.RestAuthenticatedClient());
    gh.singleton<_i3.RestClient>(_i3.RestClient());
    gh.singleton<_i4.AuthenticationRepository>(_i4.AuthenticationRepository());
    gh.singleton<_i5.GardenRepository>(_i5.GardenRepository());
    gh.singleton<_i6.ImageRepository>(_i6.ImageRepository());
    gh.singleton<_i7.NoteRepository>(_i7.NoteRepository());
    gh.singleton<_i8.PatchRepository>(_i8.PatchRepository());
    gh.singleton<_i9.QuestionRepository>(_i9.QuestionRepository());
    gh.singleton<_i10.UserRepository>(_i10.UserRepository());
    gh.singleton<_i11.GardenService>(_i11.GardenService());
    gh.singleton<_i12.ImageService>(_i12.ImageService());
    gh.singleton<_i13.JwtAuthenticationService>(
        _i13.JwtAuthenticationService());
    gh.singleton<_i14.NoteService>(_i14.NoteService());
    gh.singleton<_i15.PatchService>(_i15.PatchService());
    gh.singleton<_i16.QuestionService>(_i16.QuestionService());
    return this;
  }
}
