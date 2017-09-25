# pricetime

This application has a single endpoint that allows a user to query against a locally stored JSON file in order to retrieve a price for a fictitious parking service.

<b>Get Started</b>

Clone the repo and run `sbt` and `sbt run`.

The application defaults to `http://localhost:8080`.

In order to query the API, go to `/isodatetime/<isodatetime>` and replace `<isodatetime>` with an actual isodatetime, such as `2015-07-04T07:00:00Z`.

Metrics *should* return at the `/metrics` endpoint.*

The response defaults to json, but in order toggle to xml, add a query parameter to your request. Ex: `/isodatetime/<isodatetime>?<any>=xml`. Any other (or no) query parameter will default to json.

<b>Known Issues</b>
- Although the metrics endpoint is in place, and several metrices are registered, I have so far been unable to get Metrics actually   reporting.
- Although the json/xml query parameter is not the proper way to toggle, I have so far been unable to get http4s library's headers object to actually return request headers, and I felt compelled to meet the `JSON & XML` requirement in some fashion.

<b>Code coverage</b>
- 17 unite and integration tests are in place and passing

<b>To Do</b>
- Remove redundant / unnecessary packages
- Add scalastyle
- replace json file with database

<b>Versions</b>
- Scala Version: 2.12.2
- SBT Version: 0.13

<b>License</b>

MIT License

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
