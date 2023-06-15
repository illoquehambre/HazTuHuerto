
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:haz_tu_huerto_mobile_2/blocs/garden_details/garden_details_bloc.dart';
import 'package:haz_tu_huerto_mobile_2/blocs/note_details/note_details_bloc.dart';
import 'package:haz_tu_huerto_mobile_2/config/locator.dart';
import 'package:haz_tu_huerto_mobile_2/services/note_service.dart';
import 'package:haz_tu_huerto_mobile_2/widget/note_details.dart';
import 'package:loading_animation_widget/loading_animation_widget.dart';

class NoteDetailsPage extends StatelessWidget {
  final int id;
  const NoteDetailsPage({super.key,required this.id});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Note Details'),
      ),
      body:BlocProvider(
      create: (context) {
        final noteService = getIt<NoteService>();
        return NoteDetailsBloc(noteService)..add(NoteDetailsInitialEvent(id: id));
      },
      child: const EventsPageSFG(),
      ),
    );
  }
}

class EventsPageSFG extends StatefulWidget {
  const EventsPageSFG({super.key});

  @override
  State<EventsPageSFG> createState() => _EventsPageSFGState();
}

class _EventsPageSFGState extends State<EventsPageSFG> {
  @override
  Widget build(BuildContext context) {
    return BlocBuilder<NoteDetailsBloc, NoteDetailsState>(
      builder: (context,  state) {
        if (state is NoteDetailsSucces) {
          return NoteDetails(
            context: context,
            note: state.note,
          );
        } else if (state is GardenDetailsFailure) {
          return const Center(
            child: Text("Ha ocurrido un error a la hora de cargar los posts"),
          );
        } else {
          return Center(
            child: LoadingAnimationWidget.staggeredDotsWave(
                color: Colors.white, size: 40),
          );
        }
      },
    );
  }
}