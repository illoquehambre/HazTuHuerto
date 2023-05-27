part of 'garden_details_bloc.dart';

abstract class GardenDetailsEvent extends Equatable {
  const GardenDetailsEvent();

  @override
  List<Object> get props => [];
}
class GardenDetailsInitialEvent extends GardenDetailsEvent {
   final String id;

  const GardenDetailsInitialEvent({required this.id});

}




