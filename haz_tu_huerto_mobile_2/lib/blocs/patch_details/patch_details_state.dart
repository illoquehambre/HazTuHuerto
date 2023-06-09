part of 'patch_details_bloc.dart';

abstract class PatchDetailsState extends Equatable {
  const PatchDetailsState();

  @override
  List<Object> get props => [];
}

class PatchDetailsInitial extends PatchDetailsState {}

class PatchDetailsSucces extends PatchDetailsState {
  final dynamic patch;

  const PatchDetailsSucces({required this.patch});

  @override
  List<Object> get props => [patch];
}

class PatchDetailsLoading extends PatchDetailsState {}

class PatchDetailsFailure extends PatchDetailsState {
  final String error;

  const PatchDetailsFailure({required this.error});

  @override
  List<Object> get props => [error];
}
