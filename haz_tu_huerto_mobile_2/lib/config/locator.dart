
import 'package:get_it/get_it.dart';
import 'package:haz_tu_huerto_mobile_2/config/locator.config.dart'; 
import 'package:injectable/injectable.dart';

import '../services/localstorage_service.dart'; 

final getIt = GetIt.instance;


@InjectableInit()
void configureDependencies() => getIt.init();

void setupAsyncDependencies() {
  //var localStorageService = await LocalStorageService.getInstance();
  //getIt.registerSingleton(localStorageService);
  getIt.registerSingletonAsync<LocalStorageService>(() => LocalStorageService.getInstance());
}