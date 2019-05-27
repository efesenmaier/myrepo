import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Solution {

    static public void doIt(String[] input) {
        DependencyManager dependencyManager = new DependencyManager();

        for (int i = 0; i < input.length; ++i) {
            String line = input[i];

            Scanner scanner = new Scanner(line);
            if (!scanner.hasNext()) {
                System.out.println("Empty command line");
                continue;
            }

            String commandStr = scanner.next();

            List<String> componentNames = new ArrayList<>();
            while (scanner.hasNext()) {
                componentNames.add(scanner.next());
            }

            // Echo the command line
            System.out.println(line);

            if ("DEPEND".equals(commandStr)) {
                dependencyManager.doDepend(componentNames);
            } else if ("INSTALL".equals(commandStr)) {
                if (componentNames.size() > 0) {
                    String componentName = componentNames.get(0);
                    dependencyManager.doInstall(componentName);
                }
            } else if ("REMOVE".equals(commandStr)) {
                if (componentNames.size() > 0) {
                    String componentName = componentNames.get(0);
                    dependencyManager.doRemove(componentName);
                }
            } else if ("LIST".equals(commandStr)) {
                dependencyManager.doList();
            }
        }

        System.out.flush();
    }

    public static class Component {
        final String name;

        LinkedHashSet<String> dependsOn = new LinkedHashSet<>();
        LinkedHashSet<String> requiredBy = new LinkedHashSet<>();

        // Cached full dependency lists
        LinkedHashSet<String> fullDependsOn = null;
        LinkedHashSet<String> fullRequiredBy = null;

        public Component(String name) {
            this.name = name;
        }
    }

    public static class CycleDetectedException extends RuntimeException {
        public CycleDetectedException(String s) {
            super(s);
        }
    }

    public static class DependencyManager {
        private Map<String, Component> components = new HashMap<>();

        private Map<String, Boolean> installedComponents = new LinkedHashMap<>();

        private Component getOrCreateComponent(String name) {
            Component component = components.get(name);
            if (component == null) {
                component = new Component(name);
                components.put(name, component);
            }
            return component;
        }

        private Component getComponent(String name) {
            Component c = components.get(name);
            assert c != null;
            return c;
        }

        void doDepend(List<String> componentNames) {
            if (componentNames == null || componentNames.size() < 2) {
                throw new IllegalArgumentException("DEPENDS requires at least 2 arguments");
            }

            List<Component> components = new ArrayList<>();
            for (String name : componentNames) {
                components.add(getOrCreateComponent(name));
            }

            Component c1 = components.get(0);

            for (int i = 1; i < components.size(); ++i) {
                Component c2 = components.get(i);
                addDependency(c1, c2);
            }
        }

        private void addDependency(Component c1, Component c2) {
            if (c1 == null || c2 == null || c1 == c2) {
                // Note:  Reference comparison works since system only creates component
                return;
            }

            // Check dependencies of c2
            LinkedHashSet<String> c2Dependencies = findDependencies(c2, false);
            if (c2Dependencies.contains(c1.name)) {
                System.out.println(c2.name + " depends on " + c1.name + ", ignoring command");
                return;
            }

            c1.dependsOn.add(c2.name);
            c2.requiredBy.add(c1.name);
        }

        void doInstall(String componentToInstall) {
            if (isInstalled(componentToInstall)) {
                System.out.println(componentToInstall + " is already installed");
                return;
            }

            Component component = getOrCreateComponent(componentToInstall);

            LinkedHashSet<String> dependencies = findDependenciesWithCaching(component, false);

            LinkedList<String> dependenciesReversed = new LinkedList<>();
            dependencies.stream()
                    .forEach(name -> dependenciesReversed.addFirst(name));

            dependenciesReversed.stream()
                    .forEach(name -> installIfNeeded(name, false));

            installIfNeeded(componentToInstall, true);
        }

        void installIfNeeded(String componentName, boolean explicit) {
            assert components.get(componentName).dependsOn.stream().allMatch(name -> isInstalled(name));

            if (!isInstalled(componentName)) {
                installedComponents.put(componentName, explicit);
                System.out.println("Installing " + componentName);
            } else if (explicit && !installedComponents.get(componentName)) {
                // Silently upgrade to explicit install if not explicit
                installedComponents.put(componentName, explicit);
            }
        }

        LinkedHashSet<String> findDependenciesWithCaching(Component source, boolean searchRequiredBy) {
            if (searchRequiredBy && source.fullRequiredBy != null) {
                return source.fullRequiredBy;
            } else if (!searchRequiredBy && source.fullDependsOn != null) {
                return source.fullDependsOn;
            }

            LinkedHashSet<String> dependencies = findDependencies(source, searchRequiredBy);

            // Update cached dependency/requiredBy list in component since dependendency info is immutable once
            // searches begin
            if (searchRequiredBy) {
                source.fullRequiredBy = dependencies;
            } else {
                source.fullDependsOn = dependencies;
            }
            return dependencies;
        }

        /**
         * Returns dependencies as found by DFS. Components found later in the search are
         * re-appended at the end of the list to ensure dependency ordering.
         *
         * @param source
         * @return
         */
        LinkedHashSet<String> findDependencies(Component source, boolean searchRequiredBy) {
            LinkedHashSet<String> dependencies = new LinkedHashSet<>();

            Deque<Component> stack = new ArrayDeque<>();
            stack.push(source);

            while (!stack.isEmpty()) {
                Component cur = stack.pop();
                if (cur != source) {
                    // Remove the component (if present)
                    dependencies.remove(cur.name);

                    // Re-append the item at the end of the ordered hash set
                    dependencies.add(cur.name);
                }

                LinkedHashSet<String> children = searchRequiredBy ? cur.requiredBy : cur.dependsOn;

                // Reverse the ordering of children so the first dependency (as defined in DEPEND command)
                // is explored first by DFS to match test case
                LinkedList<String> childrenReversed = new LinkedList<>();
                children.stream().forEach(name -> childrenReversed.addFirst(name));

                for (String name : childrenReversed) {
                    if (name.equals(source.name)) {
                        throw new CycleDetectedException("Dependency cycle detected");
                    }

                    stack.push(components.get(name));
                }
            }

            return dependencies;
        }

        void doRemove(String componentToRemove) {
            // Nothing to do if not installed
            if (!isInstalled(componentToRemove)) {
                System.out.println(componentToRemove + " is not installed");
                return;
            }

            Component component = getComponent(componentToRemove);

            // Any installed components that require this component MUST be explicitly installed OR a dependency
            // of an explicitly installed component since all implicit dependencies are removed.
            if (component.requiredBy.stream().anyMatch(n -> isInstalled(n))) {
                System.out.println(componentToRemove + " is still needed");
                return;
            }

            removeComponent(componentToRemove);

            // Find the dependencies of the room component
            LinkedHashSet<String> dependencies = findDependenciesWithCaching(component, false);

            // Remove any that are implicitly installed and are not required by any explicitly installed components
            // By the same logic above, if any component that requires this component is installed, then there must be
            // an explicitly installed component that requires this one since implicitly installed dependencies are removed.
            for (Iterator<String> i = dependencies.iterator(); i.hasNext();) {
                String dependencyName = i.next();
                if (isImplicityInstalled(dependencyName) && getComponent(dependencyName).requiredBy.stream().noneMatch(n -> isInstalled(n))) {
                    removeComponent(dependencyName);
                }
            }
        }

        void removeComponent(String component) {
            assert isInstalled(component);
            // For debugging, assert the deep check
            assert !isRequiredByExplicitlyInstalledComponentDeepCheck(component);

            // Remove the root component
            System.out.println("Removing " + component);
            installedComponents.remove(component);
        }

        boolean isInstalled(String name) {
            return installedComponents.containsKey(name);
        }

        boolean isImplicityInstalled(String name) {
            return Boolean.FALSE.equals(installedComponents.get(name));
        }

        boolean isRequiredByExplicitlyInstalledComponentDeepCheck(String componentName) {
            Component component = getComponent(componentName);
            LinkedHashSet<String> requiredBy = findDependenciesWithCaching(component, true);
            return requiredBy.stream().anyMatch(dependencyName -> isExplicitlyInstalled(dependencyName));
        }

        boolean isExplicitlyInstalled(String name) {
            return Boolean.TRUE.equals(installedComponents.get(name));
        }

        void doList() {
            for (Map.Entry<String, Boolean> entry : installedComponents.entrySet()) {
                String name = entry.getKey();
                //Boolean explicit = entry.getValue();
                System.out.println(name);
            }
        }
    }
}
