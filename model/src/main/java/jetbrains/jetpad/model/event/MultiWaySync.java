/*
 * Copyright 2012-2016 JetBrains s.r.o
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jetbrains.jetpad.model.event;

import jetbrains.jetpad.base.Registration;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for implementing multi-way synchronizations. It prevents infinite recursive updates
 * by using an internal flag.
 */
public class MultiWaySync {
  private boolean myInSync = false;
  private List<Runnable> myWhenInSync;

  public boolean isInSync() {
    return myInSync;
  }

  public <EventT> EventSource<EventT> inSync(final EventSource<? extends EventT> source) {
    return new EventSource<EventT>() {
      @Override
      public Registration addHandler(final EventHandler<? super EventT> handler) {
        return source.addHandler(new EventHandler<EventT>() {
          @Override
          public void onEvent(final EventT event) {
            sync(new Runnable() {
              @Override
              public void run() {
                handler.onEvent(event);
              }
            });
          }
        });
      }
    };
  }

  public void startSync() {
    if (myInSync) {
      throw new IllegalStateException();
    }
    myInSync = true;
  }

  public void finishSync() {
    if (!myInSync) {
      throw new IllegalStateException();
    }

    myInSync = false;
    if (myWhenInSync != null) {
      for (Runnable r : myWhenInSync) {
        r.run();
      }
      myWhenInSync = null;
    }
  }

  public void sync(Runnable action) {
    if (myInSync) return;

    startSync();
    try {
      action.run();
    } finally {
      finishSync();
    }
  }

  public void whenInSync(Runnable r) {
    if (!myInSync) {
      r.run();
      return;
    }

    if (myWhenInSync == null) {
      myWhenInSync = new ArrayList<>();
    }
    myWhenInSync.add(r);
  }
}