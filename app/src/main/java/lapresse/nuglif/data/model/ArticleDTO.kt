package lapresse.nuglif.data.model

data class ArticleDTO(
    val channelName: String,
    val className: String,
    val dataUrl: String,
    val id: String,
    val lead: String,
    val modificationDate: String,
    val publicationDate: String,
    val sharedId: String,
    val title: String,
    val type: String,
    val visual: List<VisualDTO>
)