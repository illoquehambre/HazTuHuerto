import 'package:flutter/material.dart';
import 'package:haz_tu_huerto_mobile_2/models/garden/garden_dto.dart';
import 'package:haz_tu_huerto_mobile_2/pages/garden_details_page.dart';
import 'package:haz_tu_huerto_mobile_2/rest/rest_client.dart';

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
        child: GestureDetector(
            onTap: () {
              Navigator.push(
                context,
                PageRouteBuilder(
                  transitionDuration: const Duration(milliseconds: 500),
                  transitionsBuilder:
                      (context, animation, secondaryAnimation, child) {
                    return FadeTransition(
                      opacity: animation,
                      child: child,
                    );
                  },
                  pageBuilder: (context, animation, secondaryAnimation) {
                    return GardenDetailsPage(id: widget.garden.id);
                  },
                ),
              );
            },
            child: Card(
              shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(10)),
              margin: EdgeInsets.all(15),
              elevation: 10,
              child: Column(
                children: <Widget>[
                  ListTile(
                    contentPadding: EdgeInsets.fromLTRB(15, 10, 25, 0),
                    title: Text(widget.garden.name),
                    subtitle: Text(
                        widget.garden.latitude + ' ' + widget.garden.longitude),
                    leading: Text(widget.garden.id, style: textTheme.bodySmall),
                  ),
                  Container(
                    constraints: BoxConstraints(
                        maxHeight: 250), // Establece la altura máxima deseada
                    child: Image.network(
                      ApiConstants.baseUrl + "/download/${widget.garden.img}",
                      fit: BoxFit.cover,
                    ),
                  ),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.end,
                    children: <Widget>[
                      Text(widget.garden.numPatch.toString()),
                    ],
                  )
                ],
              ),
            )));
  }

  _onScroll() {
    setState(() {});
  }
}
