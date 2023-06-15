import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:haz_tu_huerto_mobile_2/blocs/blocs.dart';
import 'package:haz_tu_huerto_mobile_2/blocs/register/register.dart';
import 'package:haz_tu_huerto_mobile_2/config/locator.dart';
import 'package:haz_tu_huerto_mobile_2/pages/login_page.dart';
import 'package:haz_tu_huerto_mobile_2/services/services.dart';

class RegisterPage extends StatelessWidget {
  const RegisterPage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Register'),
      ),
      body: SafeArea(
          minimum: const EdgeInsets.all(16),
          child: BlocBuilder<AuthenticationBloc, AuthenticationState>(
            builder: (context, state) {
              final authBloc = BlocProvider.of<AuthenticationBloc>(context);
              if (state is AuthenticationNotAuthenticated) {
                return _AuthForm();
              }
              if (state is AuthenticationFailure ||
                  state is SessionExpiredState) {
                var msg = (state as AuthenticationFailure).message;
                return Center(
                    child: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  crossAxisAlignment: CrossAxisAlignment.center,
                  children: <Widget>[
                    Text(msg),
                    TextButton(
                      //textColor: Theme.of(context).primaryColor,
                      child: const Text('Retry'),
                      onPressed: () {
                        authBloc.add(AppLoaded());
                      },
                    )
                  ],
                ));
              }
              // return splash screen
              return const Center(
                child: CircularProgressIndicator(
                  strokeWidth: 2,
                ),
              );
            },
          )),
    );
  }
}

class _AuthForm extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    //final authService = RepositoryProvider.of<AuthenticationService>(context);
    final authService = getIt<JwtAuthenticationService>();
    final authBloc = BlocProvider.of<AuthenticationBloc>(context);

    return Container(
      alignment: Alignment.center,
      child: BlocProvider<RegisterBloc>(
        create: (context) => RegisterBloc(authBloc, authService),
        child: _SignInForm(),
      ),
    );
  }
}

class _SignInForm extends StatefulWidget {
  @override
  __SignInFormState createState() => __SignInFormState();
}

class __SignInFormState extends State<_SignInForm> {
  final GlobalKey<FormState> _key = GlobalKey<FormState>();
  final _usernameController = TextEditingController();
  final _passwordController = TextEditingController();
  final _verifyPasswordController = TextEditingController();
  final _fullNameController = TextEditingController();
  final _emailController = TextEditingController();
  final _verifyEmailController = TextEditingController();
  bool _autoValidate = false;

  @override
  Widget build(BuildContext context) {
    final registerBloc = BlocProvider.of<RegisterBloc>(context);

    _onSignupButtonPressed() {
      if (_key.currentState!.validate()) {
        registerBloc.add(SignUp(
            username: _usernameController.text,
            email: _emailController.text,
            verifyEmail: _verifyEmailController.text,
            fullName: _fullNameController.text,
            password: _passwordController.text,
            verifyPassword: _verifyPasswordController.text));
      } else {
        setState(() {
          _autoValidate = true;
        });
      }
    }

    return BlocListener<RegisterBloc, RegisterState>(
      listener: (context, state) {
        if (state is RegisterFailure) {
          _showError(state.error);
        }
      },
      child: BlocBuilder<RegisterBloc, RegisterState>(
        builder: (context, state) {
          if (state is RegisterLoading) {
            return const Center(
              child: CircularProgressIndicator(),
            );
          }
          return Material(
            child: Column(
              children: <Widget>[
                ClipOval(
                  // Establece la altura máxima deseada
                  child: Image(
                    image: AssetImage("assets/logo.png"),
                    fit: BoxFit.cover,
                    height: 150,
                  ),
                ),
                Form(
                  key: _key,
                  autovalidateMode: _autoValidate
                      ? AutovalidateMode.always
                      : AutovalidateMode.disabled,
                  child: SingleChildScrollView(
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.stretch,
                      children: <Widget>[
                        TextFormField(
                          decoration: const InputDecoration(
                            labelText: 'User Name',
                            filled: true,
                            isDense: true,
                          ),
                          controller: _usernameController,
                          keyboardType: TextInputType.text,
                          autocorrect: false,
                          validator: (value) {
                            if (value == null) {
                              return 'Username is required.';
                            }
                            return null;
                          },
                        ),
                        const SizedBox(
                          height: 12,
                        ),
                        TextFormField(
                          decoration: const InputDecoration(
                            labelText: 'Email address',
                            filled: true,
                            isDense: true,
                          ),
                          controller: _emailController,
                          keyboardType: TextInputType.emailAddress,
                          autocorrect: false,
                          validator: (value) {
                            if (value == null) {
                              return 'Email is required.';
                            }
                            return null;
                          },
                        ),
                        const SizedBox(
                          height: 12,
                        ),
                        TextFormField(
                          decoration: const InputDecoration(
                            labelText: 'Verify Email address',
                            filled: true,
                            isDense: true,
                          ),
                          controller: _verifyEmailController,
                          keyboardType: TextInputType.emailAddress,
                          autocorrect: false,
                          validator: (value) {
                            if (value == null) {
                              return 'Verify Email is required.';
                            }
                            return null;
                          },
                        ),
                        const SizedBox(
                          height: 12,
                        ),
                        TextFormField(
                          decoration: const InputDecoration(
                            labelText: 'Full Name',
                            filled: true,
                            isDense: true,
                          ),
                          controller: _fullNameController,
                          keyboardType: TextInputType.text,
                          autocorrect: false,
                          validator: (value) {
                            if (value == null) {
                              return 'Fullname is required.';
                            }
                            return null;
                          },
                        ),
                        const SizedBox(
                          height: 12,
                        ),
                        TextFormField(
                          decoration: const InputDecoration(
                            labelText: 'Password',
                            filled: true,
                            isDense: true,
                          ),
                          obscureText: true,
                          controller: _passwordController,
                          validator: (value) {
                            if (value == null) {
                              return 'Password is required.';
                            }
                            return null;
                          },
                        ),
                        const SizedBox(
                          height: 16,
                        ),
                        TextFormField(
                          decoration: const InputDecoration(
                            labelText: 'Verify Password',
                            filled: true,
                            isDense: true,
                          ),
                          obscureText: true,
                          controller: _verifyPasswordController,
                          validator: (value) {
                            if (value == null) {
                              return 'Verify Password is required.';
                            }
                            return null;
                          },
                        ),
                        const SizedBox(
                          height: 16,
                        ),
                        //RaisedButton(
                        ElevatedButton(
                          //color: Theme.of(context).primaryColor,
                          //textColor: Colors.white,
                          //padding: const EdgeInsets.all(16),
                          //shape: new RoundedRectangleBorder(borderRadius: new BorderRadius.circular(8.0)),
                          onPressed: state is RegisterLoading
                              ? () {
                                  Navigator.push(
                                    context,
                                    PageRouteBuilder(
                                      transitionDuration:
                                          const Duration(milliseconds: 500),
                                      transitionsBuilder: (context, animation,
                                          secondaryAnimation, child) {
                                        return FadeTransition(
                                          opacity: animation,
                                          child: child,
                                        );
                                      },
                                      pageBuilder: (context, animation,
                                          secondaryAnimation) {
                                        return const LoginPage();
                                      },
                                    ),
                                  );
                                }
                              : _onSignupButtonPressed,
                          //color: Theme.of(context).primaryColor,
                          //textColor: Colors.white,
                          //padding: const EdgeInsets.all(16),
                          //shape: new RoundedRectangleBorder(borderRadius: new BorderRadius.circular(8.0)),
                          child: const Text('SIGN UP'),
                        )
                      ],
                    ),
                  ),
                ),
              ],
            ),
          );
        },
      ),
    );
  }

  void _showError(String error) {
    /*Scaffold.of(context).showSnackBar(SnackBar(
      content: Text(error),
      backgroundColor: Theme.of(context).errorColor,
    ));*/

    ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text(error)));
  }
}
