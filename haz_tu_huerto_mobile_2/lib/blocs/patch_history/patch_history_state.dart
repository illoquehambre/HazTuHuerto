part of 'patch_history_bloc.dart';

abstract class PatchHistoryState extends Equatable {
  const PatchHistoryState();

  @override
  List<Object> get props => [];
}

class PatchHistoryInitial extends PatchHistoryState {}

class PatchHistorySucces extends PatchHistoryState {
  final dynamic patch;

  const PatchHistorySucces({required this.patch});

  @override
  List<Object> get props => [patch];
}

class PatchHistoryLoading extends PatchHistoryState {}

class PatchHistoryFailure extends PatchHistoryState {
  final String error;

  const PatchHistoryFailure({required this.error});

  @override
  List<Object> get props => [error];
}
