part of 'garden_bloc.dart';

abstract class GardenState extends Equatable {
  const GardenState();
  
  @override
  List<Object> get props => [];
}

class GardenInitial extends GardenState {}

class GardenSucces extends GardenState {
  final dynamic garden;

  const GardenSucces({required this.garden});

  @override
  List<Object> get props => [garden];
}

class GardenLoading extends GardenState {}

class GardenFailure extends GardenState {
  final String error;

  const GardenFailure({required this.error});

  @override
  List<Object> get props => [error];
}
