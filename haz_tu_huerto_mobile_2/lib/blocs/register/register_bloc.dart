import 'package:bloc/bloc.dart';
import '../../rest/rest_client.dart';
import 'register_event.dart';
import 'register_state.dart';
import '../authentication/authentication.dart';
import '../../services/services.dart';

class RegisterBloc extends Bloc<RegisterEvent, RegisterState> {
  final AuthenticationBloc _authenticationBloc;
  final AuthenticationService _authenticationService;

  RegisterBloc(AuthenticationBloc authenticationBloc, AuthenticationService authenticationService)
      : assert(authenticationBloc != null),
        assert(authenticationService != null),
        _authenticationBloc = authenticationBloc,
        _authenticationService = authenticationService,
        super(RegisterInitial()) {
          on<SignUp>(__onSignUp);
        }


  __onSignUp(
    SignUp event,
    Emitter<RegisterState> emit,
  ) async {
    emit(RegisterLoading());
    try {
      final response = await JwtAuthenticationService().signUp(event.username, event.password, event.verifyPassword,
                                                         event.fullName,event.email, event.verifyEmail);
      if (response.statusCode==201) {
        //_authenticationBloc.add(UserLoggedIn(user: user));
        emit(RegisterSuccess());
        emit(RegisterInitial());
      } else {
        emit(RegisterFailure(error: 'Something very weird just happened'));
      }
    } on AuthenticationException catch (e) {
      emit(RegisterFailure(error: e.message));
    } on CustomException catch (err) {
      emit(RegisterFailure(error:'An unknown error occurred ${err.message}'));
    }
  }

  
}
