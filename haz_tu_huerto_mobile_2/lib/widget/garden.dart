import 'package:flutter/material.dart';
import 'package:haz_tu_huerto_mobile_2/models/garden_dto.dart';




class Garden extends StatefulWidget {
  final GardenDto garden;
  final BuildContext context;
  final int num;

  const Garden(
      {super.key,
      required this.garden,
      required this.context,
      required this.num});

  @override
  State<Garden> createState() => _GardenState();
}


 class _GardenState extends State<Garden> {
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

