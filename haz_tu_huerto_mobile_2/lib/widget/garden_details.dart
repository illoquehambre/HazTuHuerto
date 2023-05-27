import 'package:flutter/material.dart';
import 'package:haz_tu_huerto_mobile_2/blocs/garden/garden_bloc.dart';
import 'package:haz_tu_huerto_mobile_2/models/garden/garden_details_dto.dart';

class GardenDetails extends StatefulWidget {
  final GardenDetailsDto garden;
  final BuildContext context;

  const GardenDetails(
      {super.key,
      required this.garden,
      required this.context});

  @override
  State<GardenDetails> createState() => _GardenDetailsState();
}

class _GardenDetailsState extends State<GardenDetails> {
  final _scrollController = ScrollController();

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    _scrollController.addListener(_onScroll);
  }

  @override
  Widget build(BuildContext context) {
    final textTheme = Theme.of(context).textTheme;
    return Material(
      child: ListTile(
        leading: Text(widget.garden.id, style: textTheme.bodySmall),
        title: Text(widget.garden.name),
        isThreeLine: true,
        subtitle: Text(widget.garden.img),
        textColor: const Color.fromRGBO(126, 19, 126, 0.745),
        dense: true,
      ),
    );
  }

  _onScroll() {
    setState(() {});
  }
}
