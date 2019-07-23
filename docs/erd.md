## ENTITIES AND ERD DOCUMENTATION

Entity types:
* Image
* Algorithm
* Iteration

Image Attribute types:
* PK id: long
* name: String
* timestamp:date


Algorithm Attribute types:
* PK id: long
* formula:String

Iteration Attribute types:
* PK id: long
* FK image_id: long
* FK algorithm_id: long
* timestamp:date
* name: String

![ERD Diagram](erd1.png)

* [User Stories](docs/user-stories.md)
* [Wireframes](docs/wireframes.md)
* [Milestone 2](docs/milestone-2.md)
* [Data Model](docs/data-model.md)
* [JavaDocs](docs/api/com/nevdiaz/iterate/package-summary.html)
