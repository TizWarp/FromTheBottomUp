package dev.tizwarp.ftbu.api.event;

import net.minecraftforge.fml.common.eventhandler.Event;

public class FTBUClientSyncEvent
    extends Event {

  /**
   * This event is fired on the client after FTGU syncs a player's technology
   * update.
   */
  public static class Post
      extends FTBUClientSyncEvent {
    //
  }

  private FTBUClientSyncEvent() {
    //
  }
}
