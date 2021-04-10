package reprator.wipro.factlist.datasource.remote.modal

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
    "title",
    "description",
    "imageHref"
)
class Row(
    @JsonProperty("title")
    val title: String,
    @JsonProperty("description")
    val description: String,
    @JsonProperty("imageHref")
    val imageHref: String?
)

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
    "title",
    "rows"
)
class FactListEntity(
    @JsonProperty("title")
    val title: String,
    @JsonProperty("rows")
    val rows: List<Row> = emptyList()
)