package br.com.jkavdev.fullcycle.admin.catalogo.application.video.media.get;

public record GetMediaCommand(
        String videoId,
        String mediaType
) {
    public static GetMediaCommand with(final String videoId, final String aType) {
        return new GetMediaCommand(videoId, aType);
    }
}
