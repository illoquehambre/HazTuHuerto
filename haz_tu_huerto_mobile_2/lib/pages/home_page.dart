import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:haz_tu_huerto_mobile_2/rest/rest_client.dart';
import '../blocs/authentication/authentication_bloc.dart';
import '../blocs/authentication/authentication_event.dart';
import '../config/locator.dart';
import '../models/models.dart';
import '../services/authentication_service.dart';

class HomePage extends StatelessWidget {
  final User user;
  const HomePage({super.key, required this.user});

  

  @override
  Widget build(BuildContext context) {
    final authBloc = BlocProvider.of<AuthenticationBloc>(context);
    return Scaffold(
      appBar: AppBar(
        title: Text('Home Page'),
      ),
      body: SafeArea(
        minimum: const EdgeInsets.all(16),
        child: Center(
          child: Column(
            children: <Widget>[
              Image.network(
                      ApiConstants.baseUrl +
                          "/post/file/${user.avatar}",
                      fit: BoxFit.cover,
                    ),
              Text(
                'Welcome, ${user.fullName}',
                style: TextStyle(
                  fontSize: 24
                ),
              ),
              const SizedBox(
                height: 12,
              ),
              ElevatedButton(
                //textColor: Theme.of(context).primaryColor,
                /*style: TextButton.styleFrom(
                  primary: Theme.of(context).primaryColor,
                ),*/
                child: Text('Logout'),
                onPressed: (){
                  authBloc.add(UserLoggedOut());
                },
              ),
              ElevatedButton(onPressed: () async {
                print("Check");
                JwtAuthenticationService service = getIt<JwtAuthenticationService>();
                await service.getCurrentUser();
              }
              , child: Text('Check')
              
              )
            ],
          ),
        ),
      ),
    );
  }
}
