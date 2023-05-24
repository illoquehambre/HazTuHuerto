import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

import 'blocs/authentication/authentication_bloc.dart';
import 'blocs/authentication/authentication_event.dart';
import 'blocs/authentication/authentication_state.dart';
import 'config/locator.dart';
import 'pages/home_page.dart';
import 'pages/home_page2.dart';
import 'pages/home_page3.dart';
import 'pages/login_page.dart';
import 'services/authentication_service.dart';



void main() {
  //WidgetsFlutterBinding.ensureInitialized();
  //await SharedPreferences.getInstance();
  setupAsyncDependencies();
  configureDependencies();
  //await getIt.allReady();
  
    
    runApp(BlocProvider<AuthenticationBloc>(
        create: (context) {
          //GlobalContext.ctx = context;
          final authService = getIt<JwtAuthenticationService>();
          return AuthenticationBloc(authService)..add(AppLoaded());
        },
        child: MyApp(),
      ));

}

class GlobalContext {
  
  static late BuildContext ctx;

}


class MyApp extends StatelessWidget {

  //static late  AuthenticationBloc _authBloc;

  static late MyApp _instance;

  static Route route() {
    print("Enrutando al login");
    return MaterialPageRoute<void>(builder: (context) {
      var authBloc = BlocProvider.of<AuthenticationBloc>(context);
      authBloc..add(SessionExpiredEvent());
      return _instance;
    });
    /*return MaterialPageRoute<void>(builder: (context) {
      return BlocProvider<AuthenticationBloc>(create: (context) {
        final authService = getIt<JwtAuthenticationService>();
        return AuthenticationBloc(authService)..add(SessionExpiredEvent());
      }, 
      child: MyApp(),);
    });*/
  }

  MyApp() {
    _instance = this;
  }

  @override
  Widget build(BuildContext context) {
    //GlobalContext.ctx = context;
    return MaterialApp(
      title: 'Authentication Demo',
      theme: ThemeData(
        primarySwatch: Colors.teal,
      ),
      home: BlocBuilder<AuthenticationBloc, AuthenticationState>(
        builder: (context, state) {
          GlobalContext.ctx = context;
          if (state is AuthenticationAuthenticated) {
            // show home page
            return HomePage3(
              user: state.user,
            );
          }
          // otherwise show login page
          return LoginPage();
        },
      ),
    );
  }
}
