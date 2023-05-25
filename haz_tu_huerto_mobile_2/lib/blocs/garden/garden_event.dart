part of 'garden_bloc.dart';

abstract class GardenEvent extends Equatable {
  const GardenEvent();

  @override
  List<Object> get props => [];
}
class GardenInitialEvent extends GardenEvent {}

class GardenScrollEvent extends GardenEvent {}

class GardenRefreshEvent extends GardenEvent {}



