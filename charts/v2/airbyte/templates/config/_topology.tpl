
{{/* DO NOT EDIT: This file was autogenerated. */}}

{{/*
    Topology Configuration
*/}}

{{/*
Renders the global.topology.nodeSelectorLabel value
*/}}
{{- define "airbyte.topology.nodeSelectorLabel" }}
    {{- .Values.global.topology.nodeSelectorLabel | default "airbyte/node-pool" }}
{{- end }}

{{/*
Renders the topology.nodeSelectorLabel environment variable
*/}}
{{- define "airbyte.topology.nodeSelectorLabel.env" }}
- name: NODE_SELECTOR_LABEL
  valueFrom:
    configMapKeyRef:
      name: {{ .Release.Name }}-airbyte-env
      key: NODE_SELECTOR_LABEL
{{- end }}

{{/*
Renders the global.topology.nodeSelectors.mainNodePool value
*/}}
{{- define "airbyte.topology.nodeSelectors.mainNodePool" }}
    {{- .Values.global.topology.nodeSelectors.mainNodePool | default "main" }}
{{- end }}

{{/*
Renders the topology.nodeSelectors.mainNodePool environment variable
*/}}
{{- define "airbyte.topology.nodeSelectors.mainNodePool.env" }}
- name: NODE_SELECTOR_LABEL_MAIN_NODE_POOL
  valueFrom:
    configMapKeyRef:
      name: {{ .Release.Name }}-airbyte-env
      key: NODE_SELECTOR_LABEL_MAIN_NODE_POOL
{{- end }}

{{/*
Renders the global.topology.nodeSelectors.jobsNodePool value
*/}}
{{- define "airbyte.topology.nodeSelectors.jobsNodePool" }}
    {{- .Values.global.topology.nodeSelectors.jobsNodePool | default "jobs" }}
{{- end }}

{{/*
Renders the topology.nodeSelectors.jobsNodePool environment variable
*/}}
{{- define "airbyte.topology.nodeSelectors.jobsNodePool.env" }}
- name: NODE_SELECTOR_LABEL_JOBS_NODE_POOL
  valueFrom:
    configMapKeyRef:
      name: {{ .Release.Name }}-airbyte-env
      key: NODE_SELECTOR_LABEL_JOBS_NODE_POOL
{{- end }}

{{/*
Renders the global.topology.nodeSelectors.quickJobsNodePool value
*/}}
{{- define "airbyte.topology.nodeSelectors.quickJobsNodePool" }}
    {{- .Values.global.topology.nodeSelectors.quickJobsNodePool | default "quick-jobs" }}
{{- end }}

{{/*
Renders the topology.nodeSelectors.quickJobsNodePool environment variable
*/}}
{{- define "airbyte.topology.nodeSelectors.quickJobsNodePool.env" }}
- name: NODE_SELECTOR_LABEL_QUICK_JOBS_NODE_POOL
  valueFrom:
    configMapKeyRef:
      name: {{ .Release.Name }}-airbyte-env
      key: NODE_SELECTOR_LABEL_QUICK_JOBS_NODE_POOL
{{- end }}

{{/*
Renders the set of all topology environment variables
*/}}
{{- define "airbyte.topology.envs" }}
{{- include "airbyte.topology.nodeSelectorLabel.env" . }}
{{- include "airbyte.topology.nodeSelectors.mainNodePool.env" . }}
{{- include "airbyte.topology.nodeSelectors.jobsNodePool.env" . }}
{{- include "airbyte.topology.nodeSelectors.quickJobsNodePool.env" . }}
{{- end }}

{{/*
Renders the set of all topology config map variables
*/}}
{{- define "airbyte.topology.configVars" }}
NODE_SELECTOR_LABEL: {{ include "airbyte.topology.nodeSelectorLabel" . | quote }}
NODE_SELECTOR_LABEL_MAIN_NODE_POOL: {{ include "airbyte.topology.nodeSelectors.mainNodePool" . | quote }}
NODE_SELECTOR_LABEL_JOBS_NODE_POOL: {{ include "airbyte.topology.nodeSelectors.jobsNodePool" . | quote }}
NODE_SELECTOR_LABEL_QUICK_JOBS_NODE_POOL: {{ include "airbyte.topology.nodeSelectors.quickJobsNodePool" . | quote }}
{{- end }}
