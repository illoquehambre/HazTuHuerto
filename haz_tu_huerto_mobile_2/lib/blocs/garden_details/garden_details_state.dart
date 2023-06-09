part of 'garden_details_bloc.dart';

abstract class GardenDetailsState extends Equatable {
  const GardenDetailsState();

  @override
  List<Object> get props => [];
}

class GardenDetailsInitial extends GardenDetailsState {}

class GardenDetailsSucces extends GardenDetailsState {
  final dynamic garden;

  const GardenDetailsSucces({required this.garden});

  @override
  List<Object> get props => [garden];
}

class GardenDetailsLoading extends GardenDetailsState {}

class GardenDetailsFailure extends GardenDetailsState {
  final String error;

  const GardenDetailsFailure({required this.error});

  @override
  List<Object> get props => [error];
}
