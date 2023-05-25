import 'package:bloc/bloc.dart';
import 'package:bloc_concurrency/bloc_concurrency.dart';
import 'package:equatable/equatable.dart';
import 'package:haz_tu_huerto_mobile_2/models/Garden_response_dto.dart';
import 'package:haz_tu_huerto_mobile_2/services/garden_service.dart';
import 'package:stream_transform/stream_transform.dart';



part 'garden_event.dart';
part 'garden_state.dart';


const throttleDuration = Duration(milliseconds: 100);

EventTransformer<E> throttleDroppable<E>(Duration duration) {
  return (events, mapper) {
    return droppable<E>().call(events.throttle(duration), mapper);
  };
}

class GardenBloc extends Bloc<GardenEvent, GardenState> {
  int it = 0;
  var hasReachedMax = false;
  var hasReachedMaxF = false;
  dynamic fetchedGarden;
  final GardenService _GardenService;

  Future<dynamic> _fetchGarden(int startIndex) async {
    final response = await _GardenService.findAll(startIndex);
    return response;
  }
  
/*
    on<LikeAGarden>(
      (event, emit) async {
        await _likePost(event.GardenId);
      },
    );
  }
*/

  Future<void> _onGardenFetched(
    GardenEvent event,
    Emitter<GardenState> emit,
  ) async {
    try {
      if (state is GardenInitial) {
        final response = await _fetchGarden(it);

        if (response is GardenResponseDto) {
          hasReachedMax = response.last;
          fetchedGarden = response.content;
          if (response.last != true) it += 1;
        } else if (response is String) {
          fetchedGarden = response;
        }

        emit(
          GardenSucces(
            garden: fetchedGarden,
          ),
        );
      }
    } catch (_) {
      print(_);
      emit(GardenFailure(error: "$_"));
    }
  }

  Future<void> _onScrollGarden(
    GardenEvent event,
    Emitter<GardenState> emit,
  ) async {
    if (hasReachedMax == false) {
      final response = await _fetchGarden(it);
      response.content.isEmpty
          ? hasReachedMax = true
          : {
              emit(GardenSucces(
                  garden: List.of(fetchedGarden)..addAll(response.content))),
              hasReachedMax = response.last,
              if (response.last != true) it += 1
            };
    }
  }



  Future<void> _onGardenRefresh(
    GardenEvent event,
    Emitter<GardenState> emit,
  ) async {
    final response = await _fetchGarden(0);

    if (response is GardenResponseDto) {
      response.content.isEmpty
          ? hasReachedMaxF = true
          : {
              emit(
                GardenSucces(
                  garden: response.content,
                  
                ),
              ),
              it = 1,
              hasReachedMax = response.last,
              fetchedGarden = response.content,
            };
    } else {
      emit(
        GardenSucces(
          garden: fetchedGarden,
        ),
      );
    }
  }

  GardenBloc(GardenService gardenService)
      // ignore: unnecessary_null_comparison
      : assert(GardenService != null),
        _GardenService = gardenService,
        super(GardenInitial()) {
    on<GardenInitialEvent>((event, emit) async {
      await _onGardenFetched(event, emit);
    });

    on<GardenScrollEvent>(
      (event, emit) async {
        await _onScrollGarden(event, emit);
      },
      transformer: throttleDroppable(throttleDuration),
    );

    on<GardenRefreshEvent>(
      (event, emit) async {
        await _onGardenRefresh(event, emit);
      },
    );

  
/*
  Future<void> _likePost(int postId) async {
    final aux = await _GardenService.likePost(postId);

    for (var element in fetchedGarden) {
      if (element.id == aux.id) {
        element.likedByUser = aux.likedByUser;
        element.usersWhoLiked = aux.usersWhoLiked;
      }
    }

    if (fetchedFollowedGarden is List<GetPostDto>) {
      for (var element in fetchedFollowedGarden) {
        if (element.id == aux.id) {
          element.likedByUser = aux.likedByUser;
          element.usersWhoLiked = aux.usersWhoLiked;
        }
      }
    }
  }
  */
}
}
