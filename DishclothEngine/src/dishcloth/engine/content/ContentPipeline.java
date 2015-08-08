package dishcloth.engine.content;

import dishcloth.api.events.GameEvents;
import dishcloth.engine.content.importers.ReadAllLinesImporter;
import dishcloth.engine.content.importers.ShaderProgramImporter;
import dishcloth.engine.content.importers.TextureImporter;
import dishcloth.engine.content.processors.BitmapFontProcessor;
import dishcloth.engine.content.processors.ShaderProgramProcessor;
import dishcloth.engine.content.processors.TextureProcessor;
import dishcloth.api.abstractionlayer.events.EventHandler;
import dishcloth.engine.events.EventRegistry;
import dishcloth.engine.util.debug.Debug;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <b>ContentPipeline</b>
 * <p>
 * Handles content importer and processor registration, and provides methods to load and parse
 * content from disk.
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *<br>
 * Created by ASDSausage on 26.7.2015
 *
 * @see ContentManager
 */

public final class ContentPipeline {
	private static HashMap<String, AContentImporter> contentImporters = new HashMap<>();
	private static HashMap<String, AContentProcessor> contentProcessors = new HashMap<>();
	private static List<ContentPipelineExtension> pipelineExtensions = new ArrayList<>();

	private ContentPipeline() {}

	public static void registerDefaultContentPipelineExtensions() {

		registerContentType( "png", TextureImporter.class, TextureProcessor.class );
		registerContentType( "fnt", ReadAllLinesImporter.class, BitmapFontProcessor.class );
		registerContentType( "shader", ShaderProgramImporter.class, ShaderProgramProcessor.class );

		// Fire event
		EventRegistry.fireEvent( new ContentEvents.ContentPipelineExtensionRegistrationEvent() );
	}

	@EventHandler
	public static void onGameShutdownEvent(GameEvents.GameContentDisposingEvent event) {
		contentImporters.clear();
		contentProcessors.clear();
		pipelineExtensions.clear();
	}

	/**
	 * Should be called after all "mods" are done loading but before content initalization.
	 * This ensures that even mods' ContentPipelineExtensions are properly loaded.
	 */
	public static void initialize() {

		// Filter the list (Remove non-valid classes)
		List<ContentPipelineExtension> extensionCandidates = pipelineExtensions.stream()
				.filter( extension -> AContentProcessor.class.isAssignableFrom( extension.getProcessorClass() )
						&& AContentImporter.class.isAssignableFrom( extension.getImporterClass() ) )
				.collect( Collectors.toList() );

		try {
			for (ContentPipelineExtension candidate : extensionCandidates) {
				contentProcessors.put( candidate.getFileExtension(),
				                       candidate.getProcessorClass().newInstance() );
				contentImporters.put( candidate.getFileExtension(),
				                      candidate.getImporterClass().newInstance() );
			}
		} catch (IllegalAccessException | InstantiationException e) {
			Debug.logException( e, "ContentPipeline" );
		}
	}

	/**
	 * Damn java reflection. I cannot get generic parameter class, thus this method's behaviour is highly unchecked
	 * and relies completely on file extension.
	 *
	 * @param <T>            Type of the content to load
	 * @param filename       Filename of the content to load
	 * @param contentManager
	 * @return instance of T, parsed from file using content importer and processor.
	 */
	@SuppressWarnings("unchecked")
	static <T extends AContent> T importAndProcessContent(String filename, ContentManager contentManager) {
		String extension = filename.substring( filename.lastIndexOf( '.' ) + 1 ).toLowerCase();
		AContentImporter importer = contentImporters.get( extension );
		AContentProcessor processor = contentProcessors.get( extension );

		return (T) processor.process( importer.read( filename ), contentManager );
	}

	public static void registerContentType(String fileExtension,
	                                       Class<? extends AContentImporter> importerClass,
	                                       Class<? extends AContentProcessor> processorClass) {
		pipelineExtensions.add( new ContentPipelineExtension( fileExtension, importerClass, processorClass ) );
	}

	private static class ContentPipelineExtension {
		private final String fileExtension;
		private final Class<? extends AContentImporter> importerClass;
		private final Class<? extends AContentProcessor> processorClass;

		public ContentPipelineExtension(String fileExtension, Class<? extends AContentImporter> importerClass, Class<? extends AContentProcessor> processorClass) {
			this.fileExtension = fileExtension.toLowerCase().replace( ".", "" );
			this.importerClass = importerClass;
			this.processorClass = processorClass;
		}

		public String getFileExtension() {
			return fileExtension;
		}

		public Class<? extends AContentImporter> getImporterClass() {
			return importerClass;
		}

		public Class<? extends AContentProcessor> getProcessorClass() {
			return processorClass;
		}
	}
}
