import 'package:bloc/bloc.dart';
import 'package:equatable/equatable.dart';
import 'package:haz_tu_huerto_mobile_2/models/garden/garden_details_dto.dart';
import 'package:haz_tu_huerto_mobile_2/services/garden_service.dart';

part 'garden_details_event.dart';
part 'garden_details_state.dart';

class GardenDetailsBloc extends Bloc<GardenDetailsEvent, GardenDetailsState> {
  dynamic fetchedGarden;
  final GardenService _GardenService;
  
    Future<dynamic> _fetchGardenDetails(String id) async {
    final response = await _GardenService.findById(id);
    return response;
  }
  Future<void> _onGardenDetailsFetched(
    GardenDetailsInitialEvent event,
    Emitter<GardenDetailsState> emit,
  ) async {
    try {
      if (state is GardenDetailsInitial) {
        final response = await _fetchGardenDetails(event.id);

        if (response is GardenDetailsDto) {
          fetchedGarden = response;
         
        } else if (response is String) {
          fetchedGarden = response;
        }

        emit(
          GardenDetailsSucces(
            garden: fetchedGarden,
          ),
        );
      }
    } catch (_) {
      print(_);
      emit(GardenDetailsFailure(error: "$_"));
    }
  }

    GardenDetailsBloc(GardenService gardenService)
      // ignore: unnecessary_null_comparison
      : assert(GardenService != null),
        _GardenService = gardenService,
        super(GardenDetailsInitial()) {
    on<GardenDetailsInitialEvent>((event, emit ) async {
       await _onGardenDetailsFetched(event, emit);
    });
  }
 
}
